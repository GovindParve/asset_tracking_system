import React, { Component } from 'react'
import { getGatewayWiseData } from '../../Service/getGatewayWiseData'
import "./Alert.css"
let randomHex = () => {
    let letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
};
export default class Alert extends Component {
    state = {
        allData: [],
        amrList: [],
        checkedStatus: [],
        checkedValue: [],
        selectedDevice: ""
    }

    async componentDidMount() {
        let result = await getGatewayWiseData();
        // this.interval = setInterval(() => this.setState({ time: Date.now() }), 1000);
        this.setState({ allData: result && result.data })
        console.log("alldate",this.state.allData)
    }
    
    render() {
        const { allData, checkedStatus } = this.state
        return (
            <div>
              <div className='getway_tag'>
                <h3>Gateway Wise Tag</h3>
                </div>
                <br />
                <div className="card__wrapper">
                {allData.map((obj, key) => (
                
                <div className="dashbord__card card1" key={key} style={{background:randomHex()}}>
                {obj.gatewayName}
                <br />
                <span className="card__value"title="Gateway Wise Total Tag">{obj.tagCount}</span>
                </div>
                      ))}
          </div>



            </div>
        )
    }
}
