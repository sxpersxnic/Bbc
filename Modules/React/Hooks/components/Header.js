import styles from "./Header.module.css"

export default function Header({ title, subtitle}) {
    return(
        <header className={styles.header}>
            <h1>{title}</h1>
            <p>{subtitle}</p>
        </header>
    )
    
}