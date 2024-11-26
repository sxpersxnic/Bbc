import styles from "./Button.module.css"

export default function Button({ text, variant}) {

    const buttonClass = styles.button;
    const variantClass = styles[variant];

    return (
        <button className={`${buttonClass} ${variantClass}`}>
            {text}
        </button>
    );
}