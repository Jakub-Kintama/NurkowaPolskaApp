import React, {useState, useEffect} from 'react';
import './App.css';
import Home from './components/Home'

function App() {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8000/db')
    .then(res => {
      return res.json();
    })
    .then((data) => {
      setData(data);
    })
  }, []);

  return (
    <Home/>
  );
}

export default App;
