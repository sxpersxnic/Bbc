import styles from "./Shoppinglist.module.css"

export default function ShoppingList({ title, items}) {

    return (
        <div>
            <h1 className={`${styles.title}`}>{title}</h1>
            <ul>
                {items.map((item, index) => (
                    <li key={index} className={`${styles.item}`}>
                        {item.name}: {item.amount}
                    </li>
                ))}
            </ul>
        </div>
    )
}