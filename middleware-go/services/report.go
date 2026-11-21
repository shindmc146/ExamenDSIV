package services

import (
	"io"
	"middleware-go/config"
	"net/http"
	"time"
)

var client = &http.Client{Timeout: 10 * time.Second}

func GetReports() (int, []byte, error) {
	resp, err := client.Get(config.SpringURL + "/reports")
	if err != nil {
		return 0, nil, err
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return 0, nil, err
	}

	return resp.StatusCode, body, nil
}
