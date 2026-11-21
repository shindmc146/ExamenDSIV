<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { Report, ReportPayload } from '@/schema/report.types';
import ReportCard from '@/components/ReportCard.vue';
import { useReports } from '@/hooks/useReports.ts';

import { useRouter } from 'vue-router';

const router = useRouter();


const { reports, error, loadReports, handleDelete } = useReports();
onMounted(async () => {
    loadReports();
})

function handleEdit(id: number) {
    router.push(`/reports/edit/${id}`)
}


</script>

<template>

    <h1>Reportes</h1>
    <!-- <pre>{{ form }}</pre> -->
    <p v-if="error">{{ error }}</p>

    <ul>
        <ReportCard v-for="r in reports" :key="r.id" :report="r" @delete="handleDelete" @edit="handleEdit" />
    </ul>
</template>