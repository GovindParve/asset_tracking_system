import './reports.css'
const getoptions = (gatwayName,stayTime,tagName) => {
    return {
      chart: {
        type: 'column'
      },
      title: {
        text: 'Tags Stay Time In Hours'
      },
      xAxis: {
       // type: 'category',
        categories: gatwayName,
        labels: {
          rotation: -45,
          style: {
            fontSize: '10px',
            fontFamily: 'Verdana, sans-serif',
            fontSize:'13px',

          }
        }
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Hours'
        }
      },
      credits: {
        enabled: false,
      },
      legend: {
        enabled: false,
      },
      tooltip: {
        pointFormat:'<b>Tag: </b>'+ tagName + '</br><b>Stay Time in Hours:</b> {point.y:.1f}  Hours'
      },
      series: [{
        name: 'Population',
        data: stayTime,
        // color: {
        //   linearGradient: { x1: 0, x2: 0, y1: 0, y2: 1 },
        //   stops: [
        //     [0, '#ff0808'],
        //     [1, '#6767f4ad']
        //   ]
        // },
      
         color: 'rgb(192 22 100)',
          dataLabels: {
          enabled: true,
          rotation: 0,
          color: '#FFFFFF',
          align: 'center',
          format: '{point.y:.1f}', // one decimal
          y: 23, // 10 pixels down from the top
          style: {
            fontSize: '14px',
            fontFamily: 'Verdana, sans-serif'
          }
        }
      }]
    }
  }
  
  export default getoptions
  
