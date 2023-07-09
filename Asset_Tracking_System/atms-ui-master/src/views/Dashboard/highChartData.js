import Highcharts from 'highcharts';
import './Dashboard.css'
const getoptions = (total,newd,age) => {
  // console.log('graphdata',total)
//    console.log('graphdata',thisWeek.gatewayNames)

  return {
      
    chart: {
      type: 'column' 
  },
  title: {
      text: 'Total Tags & Aged Tags'
  },
  xAxis: {
      categories: total,
      labels: {
        rotation: -45,
        style: {
          fontSize: '13px',
          fontFamily: 'Verdana, sans-serif',
          fontWeight: '700'         
        }
      }
  },
  yAxis: {
      min: 0,
      title: {
          text: 'Asset Tags',
          fontWeight: 'bold',
      },
      stackLabels: {
          enabled: true,
          style: {
              fontWeight: 'bold',
              color: ( // theme
                  Highcharts.defaultOptions.title.style &&
                  Highcharts.defaultOptions.title.style.color
              ) || '#1614dc',
              fontSize: '16px',
              
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
      pointFormat: '{series.name}: {point.y}<br/>Total Tags: {point.stackTotal}'
  },
  plotOptions: {
      column: {
          stacking: 'normal',
          dataLabels: {
              enabled: true
          }          
      }
  },
  //rgb(192 22 100)
series: [    {
    name: 'Recent Tag',
    data: newd,
    color: 'rgb(192 22 100)',
    dataLabels: {
        enabled: true,
        rotation: 0,
        color: '#FFFFFF',
        align: 'center',
       // format: '{point.y:.1f}', // one decimal
        y: 10, // 10 pixels down from the top
        style: {
          fontSize: '18px',
          fontFamily: 'Verdana, sans-serif'
        }
      }
}, {
    name: 'Aged Tag',
    data: age,
    color: 'rgb(141 206 10)',
    dataLabels: {
        enabled: true,
        rotation: 0,
        color: '#FFFFFF',
        align: 'center',
       // format: '{point.y:.1f}', // one decimal
        y: 10, // 10 pixels down from the top
        style: {
          fontSize: '18px',
          fontFamily: 'Verdana, sans-serif'
        }
      }
},]
}
}

export default getoptions
