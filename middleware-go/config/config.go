package config

import "os"

var SpringURL = getEnv("SPRING_URL", "http://localhost:9098")
var Port = getEnv("PORT", "4000")

func getEnv(key, def string) string {
	value := os.Getenv(key)
	if value != "" {
		return value
	}

	return def
}
