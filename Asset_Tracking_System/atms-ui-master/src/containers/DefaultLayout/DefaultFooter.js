import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Widget, addResponseMessage } from 'react-chat-widget';
import './DefaultFooter.css'
import 'react-chat-widget/lib/styles.css';
import WhatsAppWidget from 'react-whatsapp-widget'
import 'react-whatsapp-widget/dist/index.css'
import Draggable, { ControlPosition } from 'react-draggable';

const propTypes = {
  children: PropTypes.node,
};

const defaultProps = {};
//const Draggable = ReactDraggable

// const handleNewUserMessage = (newMessage) => {
//   console.log(`New message incoming! ${newMessage}`);
//   Now send the message throught the backend API
//  addResponseMessage(response);

// };

class DefaultFooter extends Component {
  constructor(props) {
    super(props)
    
    this.state = { position: { x:0, y:0 }}
  }
  onStop(event, data) {
    // Viewport (wrapper)
    const documentElement = document.documentElement
    const wrapperHeight = (window.innerHeight || documentElement.clientHeight)
    const wrapperWidth = (window.innerWidth || documentElement.clientWidth)
    
    // Draggable element center coordinates (x,y)
    // Here we assume that the Draggable Button (from the question)
    // is a rectangle. But we can easily change it with whatever 
    // figure we want and fine-tune the calculation.
    // Credits: https://stackoverflow.com/a/18932029/4312466
    const center = { 
      x: data.x + (data.node.clientWidth / 2),
      y: data.y + (data.node.clientHeight / 2)
    }
    
    // The margin from the draggable's center,
    // to the viewport sides (top, left, bottom, right)
    const margin = {
      top: center.y - 0,
      left: center.x - 0,
      bottom: wrapperHeight - center.y,
      right: wrapperWidth - center.x
    }
    
    // When we get the nearest viewport side (below), then we can 
    // use these metrics to calculate the new draggable sticky `position`
    const position = {
      top: { y: 0, x: data.x },
      left: { y: data.y, x: 0 },
      bottom: { y: (wrapperHeight - data.node.clientHeight), x: data.x },
      right:  { y: data.y, x: (wrapperWidth - data.node.clientWidth)}
    }
   
    // Knowing the draggable's margins to the viewport sides,
    // now we can sort them out and get the smaller one.
    // The smallest margin defines the nearest viewport side to draggable.
    const sorted = Object.keys(margin).sort((a,b) => margin[a]-margin[b])
    const nearestSide = sorted[0]
    
    this.setState({ position: position[nearestSide] })
  }

  componentDidMount() {
    // let offset = [0, 0];
    // let divOverlay = document.getElementById("overlay");
    // let isDown = false;
    // divOverlay.addEventListener('mousedown', function (e) {
    //   isDown = true;
    //   offset = [
    //     divOverlay.offsetLeft - e.clientX,
    //     divOverlay.offsetTop - e.clientY
    //   ];
    // }, true);
    // document.addEventListener('mouseup', function () {
    //   isDown = false;
    // }, true);

    // document.addEventListener('mousemove', function (e) {
    //   e.preventDefault();
    //   if (isDown) {
    //     divOverlay.style.left = (e.clientX + offset[0]) + 'px';
    //     divOverlay.style.top = (e.clientY + offset[1]) + 'px';
    //   }
    // }, true);



    // document.getElementById('overlay').addEventListener('mousedown', mouseDown, false);
     //document.getElementById('overlay').addEventListener('touchstart');
//     this.canvas.addEventListener("touchmove", function(e) {
//       e.preventDefault();
//       touchPosition(e);
//       getCanvasPos();
//   }, false);

// let getCanvasPos = function(el) {
//   var canvas = document.getElementById(el) || this.getCanvas();
//   var _x = canvas.offsetLeft;
//   var _y = canvas.offsetTop;

//   while(canvas = canvas.offsetParent) {
//       _x += canvas.offsetLeft - canvas.scrollLeft;
//       _y += canvas.offsetTop - canvas.scrollTop;
//   }

//   return {
//       left : _x,
//       top : _y
//   }
// };

// let touchPosition = function(e) {
//   var mouseX = e.clientX - this.getCanvasPos(e.target).left + window.pageXOffset;
//   var mouseY = e.clientY - this.getCanvasPos(e.target).top + window.pageYOffset;
//   return {
//       x : mouseX,
//       y : mouseY
//   };
// }
     
  };

  render() {
    const { children, ...attributes } = this.props;

    return (
      <React.Fragment>
        <span>Embeliot &copy; 2021 </span>
        <span className="ml-auto">Powered by <a href="https://embel.co.in">Embel Technology</a></span>
        <Draggable onStop={(event, data) => this.onStop(event, data)}>
     
        <div className='handle'>
          <WhatsAppWidget phoneNumber='8888741044' id="large" />
        </div>
        </Draggable>
      </React.Fragment>
    );
  }
}

DefaultFooter.propTypes = propTypes;
DefaultFooter.defaultProps = defaultProps;

export default DefaultFooter;
