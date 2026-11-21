package handlers

import (
	"log"
	"middleware-go/services"
	"net/http"
)

func GetReports(w http.ResponseWriter, r *http.Request) {
	status, body, err := services.GetReports()
	if err != nil {
		log.Println("Error al llamar el core", err)
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusBadGateway)
		w.Write([]byte(`{"error" : "Error al llamar al servicio"}`))
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	w.Write(body)
}
