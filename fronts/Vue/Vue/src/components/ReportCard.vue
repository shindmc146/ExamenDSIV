<script setup lang="ts">
import type { Report } from '@/schema/report.types';
import { imageUrl } from '@/config/http';

defineProps<{
    report: Report
}>()
defineEmits<{
    edit: [id: number, payload: Report]
    delete: [id: number]
}>()


</script>

<template>

    <article class="card">
        <img v-if="report.pictureURL" :src="imageUrl(report.pictureURL)" alt="Foto del reporte" />
        <h2>{{ "Asunto: " + report.subject }}</h2>
        <p>{{ "Pasajero: " + report.passengerName }}</p>
        <p>{{ "Empresa: " + report.companyName }}</p>
        <p>{{ "Tipo: " + report.type }}</p>
        <p>{{ "Urgente: " + (report.isUrgent ? "Sí" : "No") }}</p>
        <p>{{ "Fecha del viaje: " + report.tripDate }}</p>
        <p>{{ "Fecha de envío: " + report.createdAt }}</p>

        <button @click="$emit('edit', report.id, report)">Editar</button>
        <button @click="$emit('delete', report.id)">Borrar</button>
    </article>
</template>

<style scoped>
.card {
    border: 1px solid #ddd;
    border-radius: 20px;
    padding: 10px;
}

.card img {
    width: 10%;
    height: 180px;
    object-fit: cover;
    border-radius: 6px;
}

.card button {
    padding: 5px;
}
</style>