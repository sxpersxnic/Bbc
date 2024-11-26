const id = new URLSearchParams(window.location.search).get("id")
const form = document.querySelector("form")

/**
 * Diese Funktion fragt den Server nach dem Post ab und fügt die Details in das Formular ein.
 * @returns {Promise<void>}
 */
async function renderEditPost() { 

  const response = await fetch("/posts/" + id)
  const json = await response.json();

  console.log(json)

  form.title.value = json.title;
  form.body.value = json.body;
}

/**
 * Diese Funktion sendet die Daten aus dem Formular zum Speichern an den Server.
 * @returns {Promise<Response>}
 */
async function savePost() { 

  let response = await fetch("/posts/" + id)
  const json = await response.json();


  const updatedTitle = form.title.value;
  const updatedBody = form.body.value;

  response = await fetch("/posts/" + id, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: id,
        title: updatedTitle,
        body: updatedBody,
        likes: json.likes,
       
      }),
  });

  if (response.ok) {
    window.location.href = "/index.html";
  } else {
    console.error("Error updating post:", response.statusText);
  }

}

form.addEventListener("submit", async(e) => {
  e.preventDefault()
  await savePost()
})

renderEditPost()
