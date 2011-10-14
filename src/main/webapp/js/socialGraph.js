(function($){
  var Renderer = function(canvas){
      var canvas = $(canvas).get(0);
      var ctx = canvas.getContext("2d");
      var gfx = arbor.Graphics(canvas);
      var particleSystem;
      var flag = 0;
      
    var that = {
      init:function(system){
          console.log("starting");
          particleSystem = system;
          particleSystem.screenSize(canvas.width, canvas.height);
          particleSystem.screenPadding(80);
          that.initMouseHandling();
      },
      redraw:function(){
          // 
          // redraw will be called repeatedly during the run whenever the node positions
          // change. the new positions for the nodes can be accessed by looking at the
          // .p attribute of a given node. however the p.x & p.y values are in the coordinates
          // of the particle system rather than the screen. you can either map them to
          // the screen yourself, or use the convenience iterators .eachNode (and .eachEdge)
          // which allow you to step through the actual node objects but also pass an
          // x,y point in the screen's coordinate system
          // 
          
          
          var accountId = $("#account").attr("accountId");
          if(particleSystem.getEdgesFrom(""+accountId).length==0 && particleSystem.getNode(""+accountId)!=undefined) {
              if(flag==1)
                return;
              flag =1;
          }
          
          gfx.clear();
          
          ctx.fillStyle = "white"
          ctx.fillRect(0,0, canvas.width, canvas.height)
          
          particleSystem.eachEdge(function(edge, pt1, pt2){
            // edge: {source:Node, target:Node, length:#, data:{}}
            // pt1:  {x:#, y:#}  source position in screen coords
            // pt2:  {x:#, y:#}  target position in screen coords

            // draw a line from pt1 to pt2
            ctx.strokeStyle = "rgba(0,0,0, .333)"
            ctx.lineWidth = 1
            ctx.beginPath()
            ctx.moveTo(pt1.x, pt1.y)
            ctx.lineTo(pt2.x, pt2.y)
            ctx.stroke()
          })

          particleSystem.eachNode(function(node, pt){
            // node: {mass:#, p:{x,y}, name:"", data:{}}
            // pt:   {x:#, y:#}  node position in screen coords

            // draw a rectangle centered at pt
            var w = 50
            if(node.data.type == 0) {
                ctx.fillStyle = "red";
            } else if(node.data.type == 1 || node.data.type == 3) {
                ctx.fillStyle = "green";
            } else {
                ctx.fillStyle = "orange";
            }
            ctx.fillRect(pt.x-w/2, pt.y-w/2, w,w)
            
            
            ctx.font = "12px Helvetica"
            ctx.textAlign = "center"
            ctx.fillStyle = '#333333';
            var name = node.data.realName==""?node.data.username:node.data.realName;
            ctx.fillText(name, pt.x, pt.y+4);
            
          })
      },
      initMouseHandling:function(){
          // no-nonsense drag and drop (thanks springy.js)
          var dragged = null;

          // set up a handler object that will initially listen for mousedowns then
          // for moves and mouseups while dragging
          var handler = {
            clicked:function(e){
              var pos = $(canvas).offset();
              _mouseP = arbor.Point(e.pageX-pos.left, e.pageY-pos.top);
              dragged = particleSystem.nearest(_mouseP);
              
              if(dragged.node.data.type == 1) {
                  expandSocialGraph(dragged.node.data.accountId, particleSystem);
                  dragged.node.data.type = 3;
              } else if(dragged.node.data.type == 2) {
                  document.location.href = 'addfriend=' + JSON.stringify(dragged.node.data);
              } else if(dragged.node.data.type == 3) {
                  document.location.href = 'friend=' + JSON.stringify(dragged.node.data);
              }
              
              if (dragged && dragged.node !== null){
                // while we're dragging, don't let physics move the node
                dragged.node.fixed = true
              }

              $(canvas).bind('mousemove', handler.dragged)
              $(window).bind('mouseup', handler.dropped)

              return false
            },
            dragged:function(e){
              var pos = $(canvas).offset();
              var s = arbor.Point(e.pageX-pos.left, e.pageY-pos.top)

              if (dragged && dragged.node !== null){
                var p = particleSystem.fromScreen(s)
                dragged.node.p = p
              }

              return false
            },

            dropped:function(e){
              if (dragged===null || dragged.node===undefined) return
              if (dragged.node !== null) dragged.node.fixed = false
              dragged.node.tempMass = 1000
              dragged = null
              $(canvas).unbind('mousemove', handler.dragged)
              $(window).unbind('mouseup', handler.dropped)
              _mouseP = null
              return false
            }
          }
          
          // start listening
          $(canvas).mousedown(handler.clicked);

        },
    };
    return that;
  };
  
  $(document).ready(function(){
      var sys = arbor.ParticleSystem(1000, 600, 0.5, true, 55, 0.02, 0.6);
      sys.renderer = Renderer("#viewport");
      var accountId = $("#account").attr("accountId");
      expandSocialGraph(accountId, sys);
  });
  
  function expandSocialGraph(friendId, sys) {
      var accountId = $("#account").attr("accountId");
      var token = $("#account").attr("token");
      $.ajax({
          type : "get",
          url : "expandSocialGraph.do",
          data : "accountId=" + accountId + "&token=" + token +"&friendId=" + friendId,
          contentType: "application/x-www-form-urlencoded; charset=UTF-8",
          beforeSend: function(x) {
              if((x) && (x.overrideMimeType))
                  x.overrideMimeType("application/j-son; charset=UTF-8");
          },
          complete : function(data) {
              var json = null;
              try {
                  eval("json=" + data.responseText + ";");
              } catch(e) {
                  console.log(e);
                  return;
              }
              if(json.result == 1) {
                  for(var i=0; i<json.friends.length; i++) {
                      var x = json.friends[i];
                      if(sys.getNode(""+x.accountId) == undefined) {
                          sys.addNode(""+x.accountId, x);
                      }
                  }
                  for(var i=0; i<json.friends.length; i++) {
                      var x = json.friends[i];
                      if(sys.getEdges(""+friendId, ""+x.accountId).length == 0 && friendId!=x.accountId) {
                          sys.addEdge(""+friendId, ""+x.accountId);
                      }
                  }
                  for(var i=0; i<json.friends.length; i++) {
                      var x = json.friends[i];
                      if(accountId == x.accountId) {
                          sys.getNode(""+x.accountId).data.type=0;
                      } else if(sys.getEdges(""+accountId, ""+x.accountId).length != 0) {
                          sys.getNode(""+x.accountId).data.type=1;
                      } else {
                          sys.getNode(""+x.accountId).data.type=2;
                      }    
                  }
              }
          }
      });
  }

})(this.jQuery);