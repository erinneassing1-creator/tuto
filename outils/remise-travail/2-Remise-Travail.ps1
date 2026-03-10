
$nomDevoir="Tuto Web Java"

# Zaclys ncloud4
$urlDepot	 = "https://ncloud4.zaclys.com"
$tokenDepot1 = "wkzESCSFbDidk3d"
$urlVisu1	 = "https://ncloud4.zaclys.com/index.php/s/CDJGcGJEkFqoFqL"
$tokenDepot2 = "HLi4J97d8jgzzBz"
$urlVisu2	 = 'https://ncloud4.zaclys.com/index.php/s/Lg87Ac5k2ZWyHNk'
$tokenDepot3 = "q597sEiHpcsSDAw"
$urlVisu3	 = 'https://ncloud4.zaclys.com/index.php/s/gYQRbMPSsdbtfa9'

cd $PSScriptRoot
cd ..\..\..

$pathDirArchive= "."

# Affiche le nom du devoir
Write-Host
Write-Host $nomDevoir
Write-Host

# Saisie du groupe
while ($groupe -notin 1,2,3,4) {
	$groupe = Read-Host -Prompt "Quel est votre groupe de TD (1, 2, 3 ou 4) : "
}

if ($groupe -in 1,4) {
	$tokenDepot = $tokenDepot1
	$urlVisu = $urlVisu1
}
if ($groupe -eq 2) {
	$tokenDepot = $tokenDepot2
	$urlVisu = $urlVisu2
}
if ($groupe -eq 3) {
	$tokenDepot = $tokenDepot3
	$urlVisu = $urlVisu3
}

# Saisie du nom et prénom
Write-Host
$nomArchive = Read-Host -Prompt "Indiquez votre nom + prénom : "
$nomArchive = $nomArchive.trim().toUpper() + ".zip"

$pathArchive = $pathDirArchive + "\" + $nomArchive

# Création de l'Archive

if (Test-Path $pathArchive) {
	Remove-Item $pathArchive
}


Compress-Archive -Path "tuto/src"  -DestinationPath $pathArchive -CompressionLevel Optimal


#$commande="curl.exe -k -T " + $pathArchive + ' -u "' + $tokenDepot +':" ' + #$urlDepot +'/' +$nomArchive
#Write-Output $commande
#Invoke-Expression $commande

$headers = @{
    "Authorization"=$("Basic $([System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($("$($tokenDepot):"))))");
    "X-Requested-With"="XMLHttpRequest";
}
Write-Output $headers

$UrlWebdav = "$($urlDepot)/public.php/webdav/$($nomArchive)"
Write-Output $UrlWebdav

Invoke-RestMethod -Uri $UrlWebdav -InFile $pathArchive -Headers $headers -Method Put

Start-Process $urlVisu
#explorer /n,$pathDirArchive

Read-Host -Prompt "Appuyez sur Entree pour continuer"