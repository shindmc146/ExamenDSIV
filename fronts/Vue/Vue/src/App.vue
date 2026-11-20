<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { Report, ReportPayload } from './schema/report.types';
import ReportCard from './components/ReportCard.vue';
import { useReports } from './hooks/useReports.ts';


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

const { reports, error, loadReports, handleDelete, handleSubmit, onImagePick } = useReports();
onMounted(async () => {
    loadReports();
})




</script>

<template>

    <form @submit.prevent="handleSubmit(form)">

        <h1>Formulario</h1><br>
        <p v-if="error != null"><span style="color: red;">{{ error }}</span></p>

        <label>Nombre del pasajero</label><br>
        <input v-model="form.passengerName" /><br>

        <label>Nombre de la compañia</label><br>
        <input v-model="form.companyName" /><br>


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



        <label>Descipción</label><br>
        <textarea v-model="form.description"></textarea><br>

        <label>Imagen relacionada</label><br>
        <input type="file" accept="image/jpeg,image/png,image/webp" @change="onImagePick"><br>


        <button type="submit">Guardar</button>


    </form>


    <h1>Reportes</h1>
    <!-- <pre>{{ form }}</pre> -->

    <ul>
        <ReportCard v-for="r in reports" :key="r.id" :report="r" @delete="handleDelete" @edit="" />
    </ul>
</template>