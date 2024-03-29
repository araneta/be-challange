import React, { useState, useEffect } from 'react';
import {Stack, TextField, Button } from '@mui/material';
import {API_URL} from './Config'

export default function IssueEditor({refresh, currentData}){
	const [formData, setFormData] = useState({
		id: '',
		title: '',
		description: ''
	});
	
	useEffect(() => {
		if(currentData){
			setFormData(currentData);
		}
			
	}, [currentData]);

	const onChange = (e) =>{
		const {name, value} = e.target;
		setFormData(prevState=> ({
			...prevState,
			[name]: value
		}));
	};
	
	const handleSubmit = async (e) => {
		e.preventDefault();
		console.log('submit');
		try {

			if(currentData){//update
				const response = await fetch(API_URL+'/issues/'+currentData.id, {
					method: 'PUT',
					headers: {
					  'Content-Type': 'application/json'
					},
					body: JSON.stringify(formData)
				  });
				if (!response.ok) {
					throw new Error('Error sending data');
				}
			}else{//add
				const response = await fetch(API_URL+'/issues', {
					method: 'POST',
					headers: {
					  'Content-Type': 'application/json'
					},
					body: JSON.stringify(formData)
				  });
				if (!response.ok) {
					throw new Error('Error sending data');
				}
			}
			
	  
			
	  
			// Reset form after successful submission
			setFormData({
			  id: '',
			  title: '',
			  description: ''
			});
	  
			console.log('Data sent successfully!');
			refresh();
		  } catch (error) {
			console.error('Error:', error);
		  }
	}	

	return(
		<div>
			<h3>Issue Form</h3>
			<form onSubmit={handleSubmit}>
			<Stack spacing={2} direction="column" sx={{marginBottom: 4}}>
				<TextField  
					label="ID" name="id" variant="outlined"
					value={formData.id} onChange={onChange}
					type="number" required
				/>
				<TextField 
					label="Title" name="title" variant="outlined" 
					value={formData.title} onChange={onChange}  required
				/>
				<TextField  
					label="Description" name="description" variant="outlined"
					value={formData.description} onChange={onChange}   required
				/>
				<Button variant="contained" type="submit">Save</Button>
			</Stack>	
			</form>
		</div>
	);
}
