import axios from 'axios'
const baseURL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:3000/api'

export const http = axios.create({
    baseURL,
    timeout: 12_000,
    headers: {
        Accept: 'application/json',
        'Content-Type':'application/json'
    }
});

export function imageUrl(fileName:string){
    return `${baseURL}/images/${fileName}`;
}