imageid=$1
echo "$imageid"
docker tag $imageid  reg.hrlyit.com:443/kubeflow/custom/spvue:v1
docker push reg.hrlyit.com:443/kubeflow/custom/spvue:v1
docker rmi reg.hrlyit.com:443/kubeflow/custom/spvue:v1
