$(document).ready(function () {
    hentFilmer();
    hentBiletter();
});

function hentFilmer() {
    $.get("/hentFilmer",function (filmer){
        formaterFilmer(filmer);
    }).fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    })
}

function formaterFilmer(filmer) {
    let ut = "<select id='valgtFilm'>";
    let forrigeFilm = "";
    ut += "<option>Velg film</option>";
    for(const enfilm of filmer) {
        if(enfilm.filmnavn !== forrigeFilm) {
            ut += "<option>" + enfilm.filmnavn +"</option>";
        }
        forrigeFilm = enfilm.filmnavn;
    }
    ut+="</select>";
    $("#Velgfilm").html(ut);
}
/*
// Denne funker kun hvis du gjør et get call på
// objektene, noe som jeg ikke har valgt å gjøre
// denne gangen men å legge de inn i obligControlleren
 function formaterFilmer(filmer) {
     let html = "<select id='valgtFilm'/>";
     for (const enfilm of filmer) {
         html += "<option>" + enfilm.filmnavn +"</option>"
     }
     html += "</select>";
     $("#Velgfilm").html(html);
 }

 */
function Antall(innData) {
    const tall = parseInt(innData);
    if(isNaN(innData) || innData <= 0) {
        $("#tomt_Antall").text("Må skrive inn noe antall")
        return;
    }
    else {
        $("#Antall").val(tall);
    }
}


function hentBiletter() {
    $.get("/hentBiletter",function (filmer) {
        formater(filmer);
    }).fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    })
}



function slettAlleBiletter() {
    $.get("/slettBiletter",function () {
        hentBiletter();
    }).fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    })
}

function formater(filmListe) {
    let ut = "<tr><th>Filmnavn</th><th>Antall</th><th>Fornavn</th><th>Etternavn</th><th>Telefonnr</th><th>Epost</th></tr>";
    for(let enFilm of filmListe) {
        ut+= '<tr>';
        ut+= '<td>'+enFilm.filmnavn +'</td>';
        ut+= '<td>'+ enFilm.antall +'</td>';
        ut+= '<td>'+ enFilm.fornavn +'</td>';
        ut+= '<td>'+ enFilm.etternavn +'</td>';
        ut+= '<td>'+ enFilm.tlf +'</td>';
        ut+= '<td>'+ enFilm.epost +'</td>';
        ut+= '<td><button class="btn btn-primary" onclick="hentBilett('+ enFilm.id +')">Endre</button></td>'
        ut+= '<td><button class="btn btn-danger" onclick="slettEnBilett('+ enFilm.id +')">Slett</button></td></br>';
        ut+= '</tr>';
    }
    $("#resultat").html(ut);
}

function hentBilett(id){
    const url = "/hentEnBilett?id=" + id;
    $.get(url, function (enBilett) {
        $("#id").val(enBilett.id);
        $("#valgtFilm").val(enBilett.filmnavn);
        $("#Antall").val(enBilett.antall);
        $("#Fornavn").val(enBilett.fornavn);
        $("#Etternavn").val(enBilett.etternavn);
        $("#Telefonnr").val(enBilett.tlf);
        $("#Epost").val(enBilett.epost);
    })
}

function lagreEndring() {
    let filmnavn = $("#valgtFilm").val();
    let antall = $("#Antall").val();
    let fornavn = $("#Fornavn").val();
    let etternavn = $("#Etternavn").val();
    let tlf = $("#Telefonnr").val();
    let epost = $("#Epost").val();

    const valgtFilm = $("#valgtFilm").val();
    if(valgtFilm === "Velg film") {
        $("#valgtfilmen").text("Velg en film");
        event.preventDefault();
        return;
    }
    filmnavn = valgtFilm;

    const antallet = $("#Antall").val();
    if(isNaN(antallet) || antallet <= 0) {
        $("#tomt_Antall").text("Må skrive inn noe antall").css('color','red');
        event.preventDefault();
        return;
    }
    antall = antallet;

    const fornavnet = $("#Fornavn").val();
    if(fornavnet === "") {
        $("#tomt_fornavn").text("Må skrive inn noe i Fornavnet!").css('color','red');
        event.preventDefault();
        return;
    }
    fornavn = fornavnet;

    const etternavnet = $("#Etternavn").val();
    if(etternavnet === "") {
        $("#tomt_Etternavn").text("Må skrive inn noe i Etternavnet").css('color','red');
        event.preventDefault();
        return;
    }
    etternavn = etternavnet;

    const telefonnummeret = $("#Telefonnr").val();
    if(telefonnummeret === "" || isNaN(telefonnummeret) || telefonnummeret.length !== 8) {
        $("#tomt_telefonnr").text("Må skrive et gyldig telefonnr på 8 siffer").css('color','red');
        event.preventDefault();
        return;
    }

    tlf = telefonnummeret;

    const eposten = $("#Epost").val();
    const epostRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!epostRegex.test(eposten)) {
        $("#tomt_epost").text("Skriv inn en gyldig e-postadresse").css('color', 'red');
        event.preventDefault();
        return;
    }
    epost = eposten;

    const bilett = {
        id : $("#id").val(),
        filmnavn : $("#valgtFilm").val(),
        antall : $("#Antall").val(),
        fornavn : $("#Fornavn").val(),
        etternavn : $("#Etternavn").val(),
        tlf : $("#Telefonnr").val(),
        epost : $("#Epost").val(),
    };
    $.post("/lagreEndringen",bilett, function () {

    })
}

function slettEnBilett(id) {
    const url = "/slettEnBilett?id=" + id;
    $.get(url, function () {
        window.location.href = "/";
    })
}
function regBilett() {
    let filmnavn = $("#valgtFilm").val();
    let antall = $("#Antall").val();
    let fornavn = $("#Fornavn").val();
    let etternavn = $("#Etternavn").val();
    let tlf = $("#Telefonnr").val();
    let epost = $("#Epost").val();

    $("#valgt_film, #tomt_Antall, #tomt_fornavn, #tomt_telefonnr, #tomt_Etternavn, #tomt_epost").text("").css('color','black');

    const valgtFilm = $("#valgtFilm").val();
    if(valgtFilm === "Velg film") {
        $("#valgtfilmen").text("Velg en film");
        event.preventDefault();
        return;
    }
    filmnavn = valgtFilm;

    const antallet = $("#Antall").val();
    if(isNaN(antallet) || antallet <= 0) {
        $("#tomt_Antall").text("Må skrive inn noe antall").css('color','red');
        event.preventDefault();
        return;
    }
    antall = antallet;

    const fornavnet = $("#Fornavn").val();
    if(fornavnet === "") {
        $("#tomt_fornavn").text("Må skrive inn noe i Fornavnet!").css('color','red');
        event.preventDefault();
        return;
    }
    fornavn = fornavnet;

    const etternavnet = $("#Etternavn").val();
    if(etternavnet === "") {
        $("#tomt_Etternavn").text("Må skrive inn noe i Etternavnet").css('color','red');
        event.preventDefault();
        return;
    }
    etternavn = etternavnet;

    const telefonnummeret = $("#Telefonnr").val();
    if(telefonnummeret === "" || isNaN(telefonnummeret) || telefonnummeret.length !== 8) {
        $("#tomt_telefonnr").text("Må skrive et gyldig telefonnr på 8 siffer").css('color','red');
        event.preventDefault();
        return;
    }

    tlf = telefonnummeret;

    const eposten = $("#Epost").val();
    const epostRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!epostRegex.test(eposten)) {
        $("#tomt_epost").text("Skriv inn en gyldig e-postadresse").css('color', 'red');
        event.preventDefault();
        return;
    }
    epost = eposten;
    const bilett = {
        filmnavn : filmnavn,
        antall : antall,
        fornavn : fornavn,
        etternavn : etternavn,
        tlf : tlf,
        epost : epost
    };
    $.post("/lagreBilett",bilett, function () {
    }).fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    })
    window.location.href="/";
}

