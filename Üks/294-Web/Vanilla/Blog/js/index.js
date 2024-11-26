const container = document.querySelector(".container")
const searchForm = document.querySelector(".search")

/**
 * Diese Funktion holt die Posts vom Server und rendert sie im HTML
 * @param searchTerm
 * @returns {Promise<void>}
 */
async function renderPosts(searchTerm) { //todo

        let url = "/posts";

        if (searchTerm) {
          url += '?q=${searchTerm}';
        }

        const response = await fetch("/posts")
        const json = await response.json();

        console.log(json);

        if (json.length === 0) {
          const noPostsMessage = document.createElement("p");
          noPostsMessage.textContent = "Keine Posts gespeichert."
          container.appendChild(noPostsMessage);

        } else {
            for(post of json){
                const postNode = document.createElement("article")
                postNode.classList.add("post")

                postNode.innerHTML = `
                  <h2>${post.title}</h2>
                  <p><small>${post.likes} Likes</small></p>
                  <p>${post.body.slice(0, 200)}...</p>
                  <a href="/details.html?id=${post.id}">Read more</a> |
                  <a href="/edit.html?id=${post.id}">Edit</a> |
                  <button class="a delete" onclick="deletePost(${post.id})">Delete</button>
                `

                container.appendChild(postNode)
              }
          }  
}

/**
 * Diese Funktion löscht einen Post mit entsprechendem Call an den Server
 * @param id
 * @returns {Promise<void>}
 */
async function deletePost(id) { //todo
  
    console.log(id);    
    await fetch("/posts/" + id, {
      method: "DELETE"
    })
      
    return renderPosts(undefined)
}

searchForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const searchTerm = searchForm.querySelector("input").value;
  container.innerHTML = "";
  renderPosts(searchTerm);
});

renderPosts();