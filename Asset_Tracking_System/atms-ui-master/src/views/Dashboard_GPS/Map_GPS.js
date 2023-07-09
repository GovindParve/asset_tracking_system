import React from 'react'
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import { withScriptjs, withGoogleMap, GoogleMap} from "react-google-maps"
import './Map_GPS.css'
import {getGps} from "../../Service/getGps"
import { getGpsTrackData } from "../../Service/getGpsTrackData"
import { listAssetTag } from "../../Service/listAssetTag"
import { getSingleGpsTag } from "../../Service/getSingleGpsTag"
import Select from "react-select";

const mapStyles = {
  // width: '100%',
  // height: '100%',
};

export class MapContainer extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      center: {
        lat: 40.854885,
        lng: -88.081807
      },
      stores: [],
    selectedDevice: "",
    gpsTagName: [],
    selectedAssetTag: "",
    allData: [],
    
    }
  }
  async componentDidMount() {
    try {
  //   setTimeout(async () => {
  //   let result = await getGpsTrackData(0);
  //   console.log('GPS', result.data)
  //   this.setState({stores:result.data},() => {
  //    console.log("GPS Data",this.state.stores)
  //     let newLat = {
  //       lat: this.state.stores[0].latitude,
  //       lng: this.state.stores[0].longitude,
  //     };
  //     this.setState({ center: newLat });
  //   }) 

  // }, 200)
  setTimeout(async () => {
    let result = await getGps();
    console.log('GPS', result.data)
    this.setState({stores:result.data},() => {
     console.log("GPS Data",this.state.stores)
      let newLat = {
        lat: this.state.stores[0].latitude,
        lng: this.state.stores[0].longitude,
      };
      this.setState({ center: newLat });
    }) 

  }, 200)
} catch (error) {
  console.log(error)
  this.setState({ loader: false })
}
    
  }
  
  onClickTag = async () =>{
    let assetTagList = await listAssetTag();
    console.log('assetTagList', assetTagList)
    let tempTagArray = []
    if (assetTagList && assetTagList.data && assetTagList.data.length != 0) {
      assetTagList.data.map((obj) => {
        let tempObj = {
          id: obj.assetTagId,
          value: obj.assetTagName,
          label: obj.assetTagName
        }
        tempTagArray.push(tempObj)
      })
    }
    this.setState({allData: tempTagArray,selectedAssetTag: assetTagList && assetTagList.data[0]
    }, () => {
      console.log('Select Tag', this.state.allData)
    })
  }
  displayMarkers = () => {
    return this.state.stores.map((store, index) => {
      return <Marker 
      //label={store.tagName} 
      key={index} id={index} position={{
      lat: store.latitude,
      lng: store.longitude,
     }} 
     onClick={() => console.log("You clicked me!")} />
    })
  }
  changeAssetTag = async (selectedOptions) => {
    console.log(selectedOptions.value)
    this.setState({ selectedDevice: selectedOptions.value }, async () => {
      let result = await getSingleGpsTag(this.state.selectedDevice)
      console.log('Gps result', result.data)
      this.setState({ gpsTagName: result.data }, () => {
        console.log('gps TagName', this.state.gpsTagName)
      })

    })
  }

  render() {
    const {gpsTagName} = this.state
    return (
      <>
      <div className='gpstracking'>
      <div className="dashboard__select__wrapper">
          {/* <div>
            <div ><b>SELECT GPS TAG</b></div>
            <div onClick={this.onClickTag}>
            <Select options={this.state.allData} className="payload__select" onChange={this.changeAssetTag} />
            </div>
          </div> */}
        </div>  
        <br />
        <br />
        <br />
        {/* {gpsTagName.map((obj, key) => ( */}        
        <div className='maptracking' 
        //key={key}
        >  
        <Map className="map"
          google={this.props.google}
          zoom={8}
          //initialCenter={{ lat: 47.444, lng: -122.176}}
          center={this.state.center}
          >
          {this.displayMarkers()}
        </Map>
        </div>
        
                {/* ))} */}
        </div>
        </>
    );
  }
}
export default GoogleApiWrapper({
  //apiKey: 'AIzaSyCYnip69lA5VyaYOszLLDEMulnAKHL2caU'
  apiKey:'AIzaSyC9MtT3OHLXy9i4E6PQ8Esm7FvsfwJy6VM'
})(MapContainer);

// import React, { Component } from "react";
// import {
//     withGoogleMap,
//     withScriptjs,
//     GoogleMap,
//     DirectionsRenderer
// } from "react-google-maps";
// class Map extends Component {
//     state = {
//         directions: null,


// };

// componentDidMount() {
//   const directionsService = new window.google.maps.DirectionsService();

//   const origin = { lat: 40.756795, lng: -73.954298 };
//   const destination = { lat: 41.756795, lng: -78.954298 };
  
//   directionsService.route(
//     {
//       origin: origin,
//       destination: destination,
//       travelMode: window.google.maps.TravelMode.DRIVING
//     },
//     (result, status) => {
//       if (status === window.google.maps.DirectionsStatus.OK) {
//         this.setState({
//           directions: result
//         });
//       } else {
//         console.error(`error fetching directions ${result}`);
//       }
//     }
//   );
// }

// render() {
//     const GoogleMapExample = withGoogleMap(props => (
//         <GoogleMap
//             defaultCenter={{ lat: 6.5244, lng:  3.3792 }}
//             defaultZoom={13}
//         >
//           <DirectionsRenderer
//       directions={this.state.directions}
//  />
//         </GoogleMap>
//     ));

//     return (
//         <div>
//             <GoogleMapExample
//                 containerElement={<div style={{ height: `500px`, width: "500px" }} />}
//                 mapElement={<div style={{ height: `100%` }} />}
//             />
//         </div>


//        );
//     }
// }

// export default Map;