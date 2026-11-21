<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { Report, ReportPayload } from '@/schema/report.types.ts';
import { useReportsForm } from '@/hooks/useReportsForm';
import { useRoute } from 'vue-router';
import { reportApi } from '@/service/reports.service';
import { useRouter } from 'vue-router'
import { useTemplateRef } from 'vue';


const form = ref<ReportPayload>({
    companyName: '',
    passengerName: '',
    type: 'COMPLAINT',
    subject: '',
    description: '',
    tripDate: '',
    pictureURL: null,
    isUrgent: false
})

const router = useRouter();
const route = useRoute()
const id = route.params.id ? Number(route.params.id) : null

const { error, submit, onImagePick, uploadedFileName, saving, success, resetFile } = useReportsForm(id);

const fileInput = useTemplateRef<HTMLInputElement>('fileInput')

async function handleSubmit() {
    if (await submit(form.value)) {
        if (id === null) {
            clearForm()
        } else {
            router.push('/')
        }

    }

}

function clearForm() {
    form.value = emptyForm()
    resetFile()
    if (fileInput.value) {
        fileInput.value.value = ''
    }
}

function emptyForm(): ReportPayload {
    return {
        companyName: '',
        passengerName: '',
        type: 'COMPLAINT',
        subject: '',
        description: '',
        tripDate: '',
        pictureURL: null,
        isUrgent: false
    }
}

onMounted(async () => {
    if (id === null) {
        return
    }

    if (!Number.isInteger(id) || id <= 0) {
        router.push("/")
        return
    }

    try {
        const r = await reportApi.getById(id)
        console.log(r.pictureURL)
        form.value = {
            companyName: r.companyName,
            passengerName: r.passengerName,
            type: r.type,
            subject: r.subject,
            description: r.description,
            tripDate: r.tripDate,
            pictureURL: r.pictureURL,
            isUrgent: r.isUrgent
        }
        uploadedFileName.value = r.pictureURL
    } catch (e) {
        error.value = "Error al obtener el reporte"
    }


})




</script>

<template>

    <form @submit.prevent="handleSubmit">

        <h1>Enviar Reporte</h1><br>
        <p v-if="error != null"><span style="color: red;">{{ error }}</span></p>
        <p v-if="success != null"><span style="color: green;">{{ success }}</span></p>
        <label>Nombre del pasajero</label><br>
        <input v-model="form.passengerName" /><br>

        <label>Empresa</label><br>
        <select v-model="form.companyName">
            <option value="">Seleccione la empresa</option>
            <option value="Tracasa">Tracasa</option>
            <option value="Buses Caribeños">Buses Caribeños</option>
            <option value="HEBA">HEBA</option>
            <option value="TRACOPA">TRACOPA</option>
            <option value="SACSA">SACSA</option>
            <option value="LUMACA">LUMACA</option>
            <option value="MEPE">MEPE</option>
            <option value="Tica Bus">Tica Bus</option>
        </select><br>

        <label>Asunto</label><br>
        <input v-model="form.subject" /><br>

        <label>Tipo de reporte</label><br>
        <select v-model="form.type">
            <option value="COMPLAINT">Queja</option>
            <option value="SUGGESTION">Sugerencia</option>
            <option value="LOSTITEM">Objeto perdido</option>
        </select><br>

        <label>Fecha del viaje</label><br>
        <input type="date" v-model="form.tripDate" /><br>

        <label>Urgente</label>
        <input type="checkbox" v-model="form.isUrgent"><br>

        <label>Descripción</label><br>
        <textarea v-model="form.description"></textarea><br>

        <label>Imagen relacionada</label><br>
        <input ref="fileInput" type="file" accept="image/jpeg,image/png,image/webp" @change="onImagePick"><br>


        <button type="submit" :disabled="saving">{{ saving ? 'Guardando...' : 'Guardar' }}</button>

    </form>
</template>