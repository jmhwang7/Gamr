<?php
function riotQuery($region, $path) {
    $url = 'https://'.$region.'.api.pvp.net/api/lol/'.$region.'/'.$path.'?api_key='.RIOT_API_KEY;

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $response = curl_exec($ch);
    $error = curl_errno($ch);
    $status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    
    if($error > 0) {
        return null;
    } else if($status >= 400) {
        return null;
    } else if(!$response) {
        return null;
    }
    
    $array = json_decode($response, true);
    return $array;
}
function getSummonerId($region, $name) {
    $array = riotQuery($region, 'v1.4/summoner/by-name/'.urlencode(strtolower($name)));
    if(!$array) {
        return null;
    }
    return $array[key($array)]['id'];
}
function getRank($region, $name) {
    $id = getSummonerId($region, $name);
    if(!$id) {
        return null;
    }
    $array = riotQuery($region, 'v2.5/league/by-summoner/'.$id.'/entry');
    if(!$array) {
        return 'Unranked';
    }
    return ucfirst(strtolower($array[$id][0]['tier']));
}
?>