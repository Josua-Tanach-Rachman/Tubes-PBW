// function showPopup() {
//     document.getElementById('popup').style.display = 'block';
//     document.getElementById('overlay').style.display = 'block';
// }

// function closePopup() {
//     document.getElementById('popup').style.display = 'none';
//     document.getElementById('overlay').style.display = 'none';
// }

// document.querySelectorAll('.detailButton').forEach(button => {
//     button.addEventListener('click', showPopup);
// });

// document.querySelector('.close').addEventListener('click', closePopup);


document.querySelectorAll('.close').forEach(button => {
    button.addEventListener('click', function () {
        console.log('Close button clicked'); // Debugging line
        document.getElementById('popupAbove').style.display = 'none';
        document.getElementById('overlayAbove').style.display = 'none';
        document.getElementById('popupBelow').style.display = 'none';
        document.getElementById('overlayBelow').style.display = 'none';
    });
});
// ---------------------------------------------------------------------------------------------
let containerUrlGambarAtas = document.querySelector("#popupAbove a");
let containerUrlGambarBawah = document.querySelector("#popupBelow a");

let array = []
function ambil(idSetlist, date){
    array = []
    let mapping = "/setlistHistory/" + idSetlist + "/" + date;
    console.log(mapping);

    fetch(mapping)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            containerUrlGambarAtas.href = "/api/uploads/images"+data.urlBukti;
            console.log(containerUrlGambarAtas);
            containerUrlGambarBawah.href = "/api/uploads/images"+data.urlBukti;
            console.log(data);
            let kategori = data.kategori;
            // Clear the existing content in both popups
            console.log(document.querySelector("#popupAbove .setlist.before tbody"));
            document.querySelector("#popupAbove .setlist.before tbody").innerHTML = ""; 
            document.querySelector("#popupBelow .setlist.before tbody").innerHTML = ""; 

            if(kategori == "lagu"){
                let container = document.querySelector("#popupAbove .setlist.before tbody")
                
                let track = data.trackBeforeAfterList;
                track.forEach(each =>{
                    console.log(each);
                    let newRow = document.createElement('tr');
                    let cell1 = document.createElement('td');
                    cell1.textContent = each['tracknumber'];

                    let cell2 = document.createElement('td');
                    if(each['namaLaguSebelumnya'] == null){
                        cell2.textContent;
                    }
                    else{
                        cell2.textContent = each['namaLaguSebelumnya']
                    }

                    let cell3 = document.createElement('td');
                    if(each['namaLaguSetelahnya'] == null){
                        cell3.textContent;
                    }
                    else{
                        cell3.textContent = each['namaLaguSetelahnya']
                    }
                    newRow.appendChild(cell1);
                    newRow.appendChild(cell2);
                    newRow.appendChild(cell3);

                    container.appendChild(newRow);
                })

                // Show the above popup
                document.getElementById('popupAbove').style.display = 'block';
                document.getElementById('overlayAbove').style.display = 'block';
                // Hide the below popup just in case it was previously shown
                document.getElementById('popupBelow').style.display = 'none';
                document.getElementById('overlayBelow').style.display = 'none';
            }
            else{
                let container = document.querySelector("#popupBelow .setlist.before tbody")

                let track = data.lokasiShowTanggal;
                console.log(track);
                track.forEach(each =>{
                    console.log(each);
                    let newRow = document.createElement('tr');
                    let cell1 = document.createElement('td');
                    cell1.textContent = each['kategori'];

                    let cell2 = document.createElement('td');
                    cell2.textContent = each['before']

                    let cell3 = document.createElement('td');
                    cell3.textContent = each['after']

                    if(each['kategori']=='tanggal'){
                        if(each['before'] != null){
                            cell2.textContent = each['before'].split(' ')[0]
                        }
                        cell3.textContent = each['after'].split(' ')[0]
                    }

                    newRow.appendChild(cell1);
                    newRow.appendChild(cell2);
                    newRow.appendChild(cell3);

                    container.appendChild(newRow);
                })

                // Show the below popup
                document.getElementById('popupBelow').style.display = 'block';
                document.getElementById('overlayBelow').style.display = 'block';

                // Hide the above popup just in case it was previously shown
                document.getElementById('popupAbove').style.display = 'none';
                document.getElementById('overlayAbove').style.display = 'none';
            }

        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
    });
}



let allBtnDetail = document.querySelectorAll(".detailButton");

allBtnDetail.forEach(btnDetail => {
    btnDetail.addEventListener("click",(event)=>{
        let anchor = btnDetail.querySelectorAll("input");
        let idSetlist = anchor[0].value;
        let date = anchor[1].value;

        ambil(idSetlist, date);
    })
});
