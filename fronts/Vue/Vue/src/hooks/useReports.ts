import { reportApi, imageApi } from "@/service/reports.service";
import { onMounted, ref } from 'vue';
import type { Report, ReportPayload } from "@/schema/report.types";
import axios from 'axios'

export function useReports(){
    const reports = ref<Report[]>([])
    const error = ref<string | null>(null)
    const loading = ref(false)

    async function loadReports() {
        error.value = null
        loading.value = true
    try {
        reports.value = await reportApi.getAll()
    } catch (e) {
         if (axios.isAxiosError(e) && e.response){
            error.value = e.response.data.error
        }else{
            error.value = 'Error al cargar los reportes'
            console.error(e)
        }
    }finally{
        loading.value = false
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


return {reports,error, loadReports, handleDelete}
}