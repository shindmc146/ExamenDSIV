package main

import (
	"fmt"
	"log"
	"middleware-go/handlers"
	"net/http"
)

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("GET /api/reports", handlers.GetReports)

	fmt.Println("Middleware Go escuchando en el puerto 4000")
	log.Fatal(http.ListenAndServe(":4000", mux))
}
