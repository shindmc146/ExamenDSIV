import {http} from '@/config/http'
import type { Report, ReportPayload } from '@/schema/report.types'


const multipart = {headers: {'Content-Type':'multipart/form-data'}}

export const reportApi = {
    async getAll(): Promise<Report[]> {
        const response = await http.get<Report[]>('/reports')
        return response.data
    },
    async getById(id:number): Promise<Report>{
        const response = await http.get<Report>(`/reports/${id}`)
        return response.data
    },
    async create(payload: ReportPayload): Promise<void>{
        const response = await http.post<void>('/reports', payload)
        return response.data
        
    },
    async update(id:number,payload:ReportPayload): Promise<void>{
        const response = await http.put<void>(`/reports/${id}`, payload)
        return response.data
    },
    async delete(id:number): Promise<void>{
        const response = await http.delete<void>(`/reports/${id}`)
        return response.data
    }

}

export const imageApi = {
    async upload(file: File): Promise<string>{
        const formData = new FormData();
        formData.append('file',file)
        const response = await http.post<{fileName: string}>('/images', formData, multipart)

        return response.data.fileName
    }
}