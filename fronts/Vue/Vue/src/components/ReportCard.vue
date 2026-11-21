<script setup lang="ts">
import type { Report } from '@/schema/report.types';
import { imageUrl } from '@/config/http';
import { normalDate } from '@/utils/changeDateFormat';
import { TYPE_LABELS } from '@/schema/report.types';

defineProps<{
    report: Report
}>()
defineEmits<{
    edit: [id: number]
    delete: [id: number]
}>()


</script>

<template>

    <article class="card">
        <img v-if="report.pictureURL" :src="imageUrl(report.pictureURL)" alt="Foto del reporte" />
        <h2>{{ "Asunto: " + report.subject }}</h2>
        <p>{{ "Pasajero: " + report.passengerName }}</p>
        <p>{{ "Empresa: " + report.companyName }}</p>
        <p>{{ "Tipo: " + TYPE_LABELS[report.type] }}</p>
        <p>{{ "Urgente: " + (report.isUrgent ? "Sí" : "No") }}</p>
        <p>{{ "Fecha del viaje: " + normalDate(report.tripDate) }}</p>
        <p>{{ "Fecha de envío: " + normalDate(report.createdAt) }}</p>
        <p>{{ "Descripción: " + report.description }}</p>

        <button @click="$emit('edit', report.id)">Editar</button>
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