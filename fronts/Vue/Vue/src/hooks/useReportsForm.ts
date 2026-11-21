import { reportApi, imageApi } from "@/service/reports.service";
import { onMounted, ref } from 'vue';
import type { Report, ReportPayload } from "@/schema/report.types";
import axios from 'axios'
import { validateReport } from "@/utils/validateReport";
import { validateImage } from "@/utils/validateImage";



export function useReportsForm(id: number | null){

    const error = ref<string | null>(null)
    const saving = ref(false)
    const success = ref<string | null>(null)
    const file = ref<File | null>(null)
    const uploadedFileName = ref<string | null>(null)


    async function submit(payload: ReportPayload): Promise<boolean>{
     error.value = null
     success.value = null
     const hasImage = file.value !== null || uploadedFileName.value !== null

    const message = validateReport(payload, hasImage)

    if (message) {
        error.value = message
        return false
    }

    saving.value = true
    try {
       
        if (file.value && !uploadedFileName.value){
            uploadedFileName.value = await imageApi.upload(file.value)
        }

        
        payload.pictureURL = uploadedFileName.value
        console.log(payload.pictureURL)

        if (id === null) {
            await reportApi.create(payload)
           success.value ='Reporte enviado correctamente!'
        }else {
            await reportApi.update(id,payload)
            success.value = 'Reporte actualizado correctamente!'
            
        }

        return true;
        
    } catch (e) {
        
        if (axios.isAxiosError(e) && e.response){
            error.value = e.response.data.error
        }else{
            error.value = 'Error al crear el reporte'
            console.error(e)
        }
        return false;
    }finally{
        saving.value = false;
    }

    
}

function resetFile(){
    file.value = null
    uploadedFileName.value = null
}


function onImagePick(event:Event) {
    const input = event.target as HTMLInputElement
    const chosenFile = input.files?.item(0) ?? null

    if (chosenFile === null) {
        return
    }

    const message = validateImage(chosenFile);
    if (message) {
        error.value = message
        input.value = ''
        return
    }

    error.value = null
    file.value = chosenFile
    uploadedFileName.value = null;

}

return {error, saving,uploadedFileName,submit, onImagePick, success, resetFile}

}