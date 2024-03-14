
import {Stack, TextField, Button } from '@mui/material';
import {API_URL} from './Config'

export default function IssueList({dataList,refresh, editIssue}){
	const deleteIssue = async(id) =>{
		
		console.log('delete');
		try {
			const response = await fetch(API_URL+'/issues/'+id, {
			  method: 'DELETE',
			  headers: {
				'Content-Type': 'application/json'
			  },			  
			});
	  
			if (!response.ok) {
			  throw new Error('Error deleting data');
			}
	  
			console.log('Data deleted successfully!');
			refresh();
		  } catch (error) {
			console.error('Error:', error);
		  }
	}

	return(
		<div>
			<h3>Issues</h3>
			
			
			<ul>
				{dataList && dataList.map(item => (
				<li key={item.id}>
					<h3>{item.title}</h3>
					<p>{item.description}</p>
					<p>
						<a href="#" onClick={()=>editIssue(item)}>Edit</a>&nbsp;
						<a href="#" onClick={()=>deleteIssue(item.id)}>Delete</a>
					</p>
					
				</li>
				))}
			</ul>
			
			
		</div>
	);
}
