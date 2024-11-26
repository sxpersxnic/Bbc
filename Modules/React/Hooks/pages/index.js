import styles from "./index.module.css"
import Counter from "../components/Counter"
import Header  from "../components/Header"
import WordReverser from "../components/WordReverser"
import ShoppingList from "../components/ShoppingList"

export default function IndexPage() {

    const centerContent = styles.center;
    const seperateContent = styles.seperate;

    return(
        <div>
            <div>
                <Header title="Counter" subtitle="On click, number increases/decreases"/>
                <div className={`${centerContent}`}>
                    <Counter/>
                </div>
            </div>

            <div className={`${seperateContent}`}></div>
            <div>
                <Header title="WordReverser" subtitle="Press enter to give word as input, click button to reverse word."/>
                <div className={`${centerContent}`}>
                    <WordReverser/>
                </div>
            </div>
            <div className={`${seperateContent}`}></div>
            <div>
                <Header title="Shopping list" subtitle="add or remove items off your list"/>
                <div className={`${centerContent}`}>
                    <ShoppingList title="Weekly shopping list"/>
                </div>
            </div>
            <div className={`${seperateContent}`}></div>
        </div>
    )
}