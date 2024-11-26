

export default function ChatUser({users}){

    return(
        <div className="align-items-center justify-center">
          <h1 className="mt-0">Contact data</h1>
      {users.map((user) => {
          console.log(user);
              return (
            <div
              key={user.id}
              className="flex align-items-center mb-3  p-2 bg-primary-reverse border-round	border-radius: var(--border-radius) justify-content-between border-solid"
            >
              <div className="flex-row  flex align-items-center p-3 gap-5 ">
                <h3>{user.username}</h3>
                <p>{user.email}</p>
            </div>
            </div>
          )
      })}
    </div>

    )

}