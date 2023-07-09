import React, { Component } from 'react'

import './AddCategory.css'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import { postCategory } from '../../Service/postCategory'
import swal from 'sweetalert';
import { getTagListByCategory } from '../../Service/getTagListByCategory'
import Axios from "../../utils/axiosInstance";
import { getAssetCategory } from '../../Service/getAssetCategory'


const SignupSchema = Yup.object().shape({    //validate our form inputs and handle the errors using YUP
   categoryName: Yup.string().required('Enter Category Name')

})
   

export default class AddCategory extends Component {
state = {
    categoryName: "",
    };
// Validation for category name
    async validateCatName(value) {
        let errors;
        let resultUser = await getAssetCategory();
        console.log("Category",resultUser)
        if (resultUser && resultUser.data && resultUser.data.length !== 0) {
            let tempUser = resultUser.data.filter((obj) => {
                return obj.categoryName === value;
            });
            if (tempUser.length !== 0) {
                errors = "This Category Name Is Already Exist";
            }
        }
        return errors;
    }


    render() {
        return (
            <div className="category_form" align="center">
                <h3>Add Tag Technology Category</h3>
                <Formik
                    initialValues={{ //declaration of initialValues
                        categoryName: '',                   
                    }}
                   
                    validationSchema={SignupSchema}  //declaration of validationSchema
                   
                    onSubmit={async values => {   //declaration of onSubmit callback
                        const data = {
                            categoryName: values.categoryName,                        
                        }                        
                        console.log('data',data);
                        let result = await postCategory(data); 
                        console.log('Category',data);
                        if (result.status === 200 ) {
                            swal("Great", "Category added successfully", "success");
                           window.location.reload();
                        }
                        else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }
                    }
                }
                >
                    {({ errors, touched }) => (                     
                        <Form>
                            <div className="categoryform">
                                <div>
                                <label className='label2'>Asset Tag Category Name</label> 
                                    <Field className="form-control" name="categoryName" placeholder="Add Asset Tag Technology Category Name" validate={this.validateCatName} />
                                    {errors.categoryName && touched.categoryName ? (
                                        <div className="error__msg">{errors.categoryName}</div>
                                    ) : null}
                                </div>
                            </div>
                            <br />
                            <div align="center"><button type="submit" className="btn btn-primary">SUBMIT</button></div>                            
                        </Form>                     
                    )}
                </Formik>
            </div>
        )
    }
}
