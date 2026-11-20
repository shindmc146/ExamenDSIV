import { reportApi, imageApi } from "@/service/reports.service";
import { onMounted, ref } from 'vue';
import type { Report, ReportPayload } from "@/schema/report.types";
import axios from 'axios'

export function useReports(){
    const reports = ref<Report[]>([])
    const error = ref<string | null>(null)


    const file = ref<File | null>(null)
    const uploadedFileName = ref<string | null>(null)

    async function loadReports() {
    try {
        reports.value = await reportApi.getAll()
    } catch (e) {
         if (axios.isAxiosError(e) && e.response){
            error.value = e.response.data.error
        }else{
            error.value = 'Error al cargar los reportes'
            console.error(e)
        }
    }
}

async function handleSubmit(payload: ReportPayload) {
    try {
        if (file.value && !uploadedFileName.value){
            uploadedFileName.value = await imageApi.upload(file.value)
        }

        payload.pictureURL = uploadedFileName.value
        await reportApi.create(payload)
        await loadReports()
    } catch (e) {
        if (axios.isAxiosError(e) && e.response){
            error.value = e.response.data.error
        }else{
            error.value = 'Error al crear el reporte'
            console.error(e)
        }
        
    }
}



    async function handleDelete(id: number) {

    if (!window.confirm('¿Está seguro que desea borrar el reporte?')) {
        return;
    }
    try {
        await reportApi.delete(id);
        await loadReports();
    } catch (e) {
         if (axios.isAxiosError(e) && e.response){
            error.value = e.response.data.error
        }else{
            error.value = 'Error al eliminar el reporte'
            console.error(e)
        }
    }
}

async function onImagePick(event:Event) {
    const input = event.target as HTMLInputElement
    file.value = input.files?.item(0) ?? null
    uploadedFileName.value = null;

}

return {reports,error, loadReports, handleSubmit, handleDelete, onImagePick}
}