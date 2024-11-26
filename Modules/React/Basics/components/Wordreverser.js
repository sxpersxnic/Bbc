import styles from "./wordreverser.module.css"

export default function WordReverser({ word }) {

  const reverseWord = word.split('').reverse().join('');
  const originalWord = styles.original;
  const reversedWord = styles.reversed;

  return (
    <div>
        <p>
            <span>original: </span>
            <span className={`${originalWord}`}>{word}</span>
        </p>

        <p>
            <span>reversed: </span>
            <span className={`${reversedWord}`}>{reverseWord}</span>
        </p>      
    </div>
  );
}
