import React, { useState, useEffect } from 'react';
import {API_URL} from './Config'
import {Stack, TextField, Grid, Paper,Box,Container } from '@mui/material';
import IssueEditor from './IssueEditor'
import IssueList from './IssueList'

function App() {
  const [refresh, setRefresh] = useState(false);
  const [currentData, setCurrentData] = useState(null);
  const [dataList,setDataList] = useState([]);

	const fetchData = async () => {
		try {
		  const response = await fetch(API_URL+'/issues');
		  if (!response.ok) {
		  	throw new Error('Failed to fetch data');
		  }
		  const data = await response.json();
      if(data){
        console.log(data);
        
        setDataList(data.data);
        setCurrentData(null);
        console.log(data.data);
        setRefresh(false);
      }
      
		} catch (error) {
		  console.error('Error fetching data:', error);
		}
  };
	
	  // Fetch data on component mount
  useEffect(() => {
    fetchData();
  }, []);
  useEffect(() => {
    fetchData();
  }, [refresh]);
	
  const onRefresh = () =>{
    setRefresh(true);
  };

  const onEdit = (data) =>{
    setCurrentData(data);
  };

  return (
    <>
    <Container >
      <Box sx={{ my: 4 }}>
      <Grid container spacing={2}>
          <Grid item xs={8}>
            <IssueEditor refresh={onRefresh} currentData={currentData} />
          </Grid>
          <Grid item xs={4}>
          <IssueList dataList={dataList} refresh={onRefresh} editIssue={onEdit} />
          </Grid>
      </Grid>
      </Box>
    </Container>
    </>
  )
}

export default App
