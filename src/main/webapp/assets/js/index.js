$(function(){
    let map=null;
    let latlng={"lat":35.868293, "lng":128.594018};
    let markers = new Array();
    let markerPositions =[
        // {"lat":35.868293, "lng":128.594018}
    ]
    if(!window.kakao || !window.kakao.maps){
        const script = document.createElement("script");
        script.src="//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=41bb0a76e49192eab5342dfdbd0a5851";
        script.addEventListener("load",()=>{
            window.kakao.maps.load(()=>{
                const container = document.getElementById("map");
                const option = {
                    center : new window.kakao.maps.LatLng(latlng.lat, latlng.lng, 16),
                    level:3
                }
                map = new window.kakao.maps.Map(container, option);
                window.kakao.maps.event.addListener(map, 'dragend', () => {
                    let center = map.getCenter();
                    latlng.lat = center.getLat();
                    latlng.lng = center.getLng();
                    console.log(latlng);
                })
                //마커 제거
                if(markers.length > 0){
                    markers.forEach((marker)=>{
                        marker.setMap(null);
                    })
                }
                markerPositions.push({"title":"마커", "latlng":new window.kakao.maps.LatLng(35.868293, 128.594018)});
                markerPositions.forEach((mark) =>{
                    //마커 추가
                    const marker = new window.kakao.maps.Marker({
                        map:map, position:mark.latlng, title:mark.title
                    })
                    markers.push(marker);
                })
            })
        })
        document.head.appendChild(script);
    }
    $.ajax({
        type:"post",
        url:"/api/realty/post/list",
        contentType:"application/json",
        data:JSON.stringify({"lat":35.865796, "lon":128.1593261, "radius":0.8}),
        success:function(r){
            realty_list=r.list;
        },
        error:function(e){
            console.log(e)
        }
    })

})