package main 

import (
	"fmt"
	"io/ioutil"
	"github.com/akamai/AkamaiOPEN-edgegrid-golang/client-v1"
	"github.com/akamai/AkamaiOPEN-edgegrid-golang/edgegrid"
	"github.com/confluentinc/confluent-kafka-go/kafka"
)

func main() {

	config, _ := edgegrid.Init("~/.edgerc", "default")

	// Retrieve all locations for diagnostic tools
	req, _ := client.NewRequest(config, "GET", "/diagnostic-tools/v2/ghost-locations/available", nil)
	resp, _ := client.Do(config, req)

	defer resp.Body.Close()
	byt, _ := ioutil.ReadAll(resp.Body)
	fmt.Println(string(byt))

}