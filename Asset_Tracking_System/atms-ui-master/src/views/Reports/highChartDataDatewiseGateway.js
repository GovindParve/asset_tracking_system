import Highcharts from 'highcharts';
import './reports.css'
const getDateWiseGatewayoptions = (gateway,total) => {
    console.log('graph Date wise Gateway data', gateway,total)
    return {      
      chart: {
        type: 'column'       
    },
    title: {
        text: 'Total Tags'
    },
    xAxis: {
      //type: 'category',
      categories: gateway,
        labels: {
          rotation: -45,
          style: {
            fontSize: '13px',
            fontFamily: 'Verdana, sans-serif',
           
          }
        }
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Asset Tags'
        },
        stackLabels: {
            enabled: true,
            style: {
                fontWeight: 'bold',
                color: ( // theme
                    Highcharts.defaultOptions.title.style &&
                    Highcharts.defaultOptions.title.style.color
                ) || 'red'
            }
        }
    },
    legend: {
        align: 'right',
        x: -30,
        verticalAlign: 'top',
        y: 25,
        fontSize:'18px',
        floating: true,
        backgroundColor:
        Highcharts.defaultOptions.legend.backgroundColor || 'white',
        borderColor: '#CCC',
        borderWidth: 1,
        shadow: false,
        
    },
    tooltip: {
        headerFormat: '<b>{point.x}</b><br/>',
        pointFormat: '{series.name}: {point.y}<br/>Total Tag: {point.stackTotal}'
    },
    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true
            }          
        }
    },
  
  series: [    {
      name: 'Total Tags',
      data: total,
      color: 'rgb(141 206 10)',
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: '#FFFFFF',
        align: 'center',
      //  format: '{point.y:.1f}', // one decimal
        y: 10, // 10 pixels down from the top
        style: {
          fontSize: '18px',
          fontFamily: 'Verdana, sans-serif'
        }
      }
  }, 
  // {
  //     name: 'Aged Tag',
  //     data: age,
  //     color: 'rgb(240 180 0)',
  //     dataLabels: {
  //       enabled: true,
  //       rotation: 0,
  //       color: '#FFFFFF',
  //       align: 'center',
  //      // format: '{point.y:.1f}', // one decimal
  //       y: 10, // 10 pixels down from the top
  //       style: {
  //         fontSize: '18px',
  //         fontFamily: 'Verdana, sans-serif'
  //       }
  //     }
  // },
]
  }
  }

export default getDateWiseGatewayoptions
