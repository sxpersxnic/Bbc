import styles from "./Thumbnail.module.css"

export default function Thumbnail({ title, views, channelName, decription, img }) { 
    return (
        <div className="thumbnail-container">
            <img src={img} alt={title}/>
            <div className="thumbnail-details">
                <h2>{title}</h2>
                <p>{views} views</p>
                <p>Channel: {channelName}</p>
                <p>{decription}</p>
            </div>
        </div>
    )
}