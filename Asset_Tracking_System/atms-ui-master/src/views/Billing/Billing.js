import React, { Component } from 'react'
import { getBillingData } from '../../Service/getBillingData'
import { AmrIDBillingList } from "../../Service/AmrIDBillingList"
import { getBillingAmrId } from "../../Service/getBillingAmrId"
import { getBillingNo } from "../../Service/getBillingNo"
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"

import { postBilling } from "../../Service/postBilling"
import Select from "react-select";
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import swal from 'sweetalert';

import "./Billing.css"
import moment from "moment";
// const SignupSchema = Yup.object().shape({
//     amrid: Yup.string(),
//         // .required('Required'),
//     sDate: Yup.string(),
    
//     eDate: Yup.string(),
//     UnitPrice: Yup.string(),
    
// });
export default class Billing extends Component {

    state = {
        basicinfo: [],
        
        AmrIdList: [],
        selectedDevice: "",
        totalliter:[],
        startDate:'',
        endDate:'',
        billingNo:'',
        Total:''
              
    }
    async componentDidMount() {
        let AmrIdList = await AmrIDBillingList();
        console.log('data',AmrIdList)
        let tempArray = []
    if (AmrIdList && AmrIdList.data && AmrIdList.data.length != 0) {
      AmrIdList.data.map((obj) => {
        let tempObj = {
          value: obj,
          label: obj
        }
        tempArray.push(tempObj)
      })
    }
    console.log('alldata',this.state.basicinfo[0])
    this.setState({
      AmrIdList: tempArray,
      selectedDevice: AmrIdList && AmrIdList.data[0]
    })
    }



    changeDevice = async (selectedOptions) => {
        console.log(selectedOptions.value)
         let resultbillingno = await getBillingNo(this.state.selectedDevice);
            this.setState({ selectedDevice: selectedOptions.value,billingNo:resultbillingno }, async () => {
                // let result = await getBillingAmrId(this.state.selectedDevice);
                // let totalresult = await getBillingData(this.state.selectedDevice);
                 console.log('billingNo',this.state.billingNo)
                // this.setState({ basicinfo: result && result.data, totalliter:totalresult && totalresult.data }, () => {
                //     console.log('alldata',this.state.basicinfo)
                //     console.log('totalliter',this.state.totalliter)
                    
                // })
            })
        
    }
    
    startdate = (e) => {
        let startDate = moment(e.target.value).format("YYYY-MM-DD")
        this.setState({ startDate: startDate }, async ()=>{
            console.log('startDate',this.state.startDate)
            if (this.state.selectedDevice !=='' && this.state.endDate != "" && this.state.startDate != "") {
                let result = await getBillingAmrId(this.state.selectedDevice);
                console.log('result',result)
                let totalresult = await getBillingData(this.state.selectedDevice, this.state.startDate, this.state.endDate);
                console.log('totalresult',totalresult)
                this.setState({ basicinfo: result && result.data, totalliter:totalresult && totalresult.data }, () => {
                    console.log('alldata',this.state.basicinfo)
                    console.log('totalliter',this.state.totalliter)
                })
            }
        }
                )
      }

      enddate = (e) => {
        let endDate = moment(e.target.value).format("YYYY-MM-DD")
        this.setState({ endDate: endDate }, async ()=>{
            console.log('enddate',this.state.endDate)
            if (this.state.selectedDevice !=='' && this.state.endDate != "" && this.state.startDate != "") {
                let result = await getBillingAmrId(this.state.selectedDevice);
                console.log('result',result)
               
                let basicInfo=result && result.data[0].split(',')
                console.log('basicInfo',basicInfo)

                let totalresult = await getBillingData(this.state.selectedDevice, this.state.startDate, this.state.endDate);
                console.log('totalresult',totalresult)
                this.setState({ basicinfo: basicInfo, totalliter:totalresult && totalresult.data }, () => {
                    console.log('alldata',this.state.basicinfo)
                    console.log('totalliter',this.state.totalliter)
                    
                })
            }
            
        }
        )
      }    
      downloadBill = async () => {
        let token = await localStorage.getItem("token");
        axios.get(`/iotmeter/billing/billPDF/${this.state.billingNo.data}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/pdf' });
                fileSaver.saveAs(blob, `${this.state.selectedDevice}_Bill_Between_${this.state.startDate}_to_${this.state.endDate}.pdf`);
            });

    }

    //   TotalPrice= async (event) => {
    //      let value=document.getElementById('UnitPrice')
         
    //   let Totlit=JSON.parse(this.state.totalliter[0]);
        
    //     var total = Totlit*value;
    //      document.getElementById('total').innerHTML= "Rs. ".concat(total);
    // }

    
render(){
    
    return (
        <>
        
          <div>
          <Formik
                    enableReinitialize={true}
                    initialValues={{
                        amrid:'',
                        sDate:'',
                        eDate:'',
                        curDate:'',
                        basicinfo:'',
                        totalliter:'',
                        UnitPrice:"",
                        Total:'',
                        billNo:''
                    }}
                    // validationSchema={SignupSchema}
                    onSubmit={async values => {
                        
                    let value=parseFloat(values.UnitPrice)
         
                    let Totlit=JSON.parse(this.state.totalliter);
        
                    var total = Totlit*value;
                    this.setState({Total:total})
                    document.getElementById('total').innerHTML= "Rs. ".concat(this.state.Total. toFixed(2));
                        console.log('values',values);
                       const currDate=moment().format("YYYY-MM-DD")
                        const data = {
                            billAmrId:this.state.selectedDevice,
    
                            startDate:this.state.startDate,
                            endDate:this.state.endDate,
                            billingDate:currDate,
                            totalLitre:this.state.totalliter[0],
                            litrePerPrice:values.UnitPrice,
                            totalAmount:total,
                            billUsername:this.state.basicinfo[0],
                            billUserId:parseInt(this.state.basicinfo[4]),
                            billAddress:this.state.basicinfo[2],
                            billCity:this.state.basicinfo[1],
                            billState:this.state.basicinfo[3],
                            billNo:this.state.billingNo.data,
                        }
                        
                        console.log('data',data);
                        let result = await postBilling(data);
                        console.log('result',result);
                        if (result.status === 200) {
                            // swal("Great", "Data added successfully", "success");
                            this.props.history.push("/billing")
                        }
                        else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }
                      
                    }}
                >          
{/* {({ errors, touched }) => ( */}
    
    <Form>
    
               <div className="Device__form__wrapper">
               <div><label><strong>SELECT AMR_ID</strong></label>
                <Select options={this.state.AmrIdList} name="amrid" className="payload__select" onChange={this.changeDevice} /></div>
                {/* {errors.amrid && touched.amrid ? <div className="error__msg">{errors.amrid}</div> : null} */}
                <div>
                    <label><strong>START DATE</strong></label>
                    <input className="form-control" type="date" name="sDate"  onChange={this.startdate} />
                    {/* {errors.sDate && touched.sDate ? <div className="error__msg">{errors.sDate}</div> : null} */}
                    </div>
                    <div>
                    <label><strong>END DATE</strong></label>
                    <input className="form-control" type="date" name="eDate"  onChange={this.enddate} />
                    {/* {errors.eDate && touched.eDate ? <div className="error__msg">{errors.eDate}</div> : null} */}
                    </div>
                    <div></div>
                    </div>
                <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                    <table className=" table table-hover table-bordered table-striped text-center">
                        <tr>
                            <th>Date</th>
                            <th>Bill No</th>
                            <th>Name & Address</th>
                            <th>AMR Id</th>
                            
                            <th>Total Consumption</th>
                            <th>Price/Liter</th>
                            
                        </tr>
                        <tbody>
                        <tr>
                        <td>{moment().format("DD-MM-YYYY")}</td>
                        <td><input type="text" value={this.state.billingNo.data} name="billno" readOnly="true" style={{color:'red',fontWeight:'600'}} /></td>
                        <td className="basicinfo">{this.state.basicinfo[0]!==undefined? this.state.basicinfo[0] + ' ' +this.state.basicinfo[1] + ' ' + this.state.basicinfo[2] +' ' + this.state.basicinfo[3]:'Select AMR, Start Date, End Date'}</td>
                            <td><input type="text" value={this.state.selectedDevice} name="amrid" readOnly="true" /></td>
                            
                            <td><input type="text" value={this.state.totalliter} name="totalliter" readOnly="true"/></td>
                            <td><Field type="text" name="UnitPrice"  placeholder="Enter Price Per Liter" />
                         
                            </td>
                            
                        </tr>
                        
                 </tbody>
                 <tfoot>
                     <tr>
                         <th colSpan="4"></th>
                         
                         <th><button type="submit" className="btn btn-primary">GET TOTAL</button></th>
                         <th id="total"></th>

                     </tr>
                     {this.state.Total!==''?<tr>
                         <th colSpan="5"></th>
                         <th><input type='button' onClick={this.downloadBill}  className="btn btn-success" value='GET INVOICE'/></th>
                         
                         

                     </tr>:<tr style={{display:'none'}}>
                         <th colSpan="5"></th>
                         <th><input type='button' onClick={this.downloadBill}   className="btn btn-success" value='GET INVOICE'/></th>
                         
                         

                     </tr>}
                     
                 </tfoot>
               </table >
                </div>
                </Form>
                {/* )} */}
                </Formik>
            </div>
        </>
    )
}
}
