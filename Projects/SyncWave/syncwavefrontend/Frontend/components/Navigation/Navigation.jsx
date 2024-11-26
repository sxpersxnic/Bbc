import {useState} from "react";

export default function Navigation({elements, ...props}){
    const [selectedIndex, setSelectedIndex] = useState(0)

    return (
        <div className="flex w-screen h-screen fixed top-0" style={{paddingTop: "102.875px"}} {...props}>
            <div className="card flex flex-column gap-3 p-3 surface-card border-noround border-top-none border-right-none">
                {elements.map((item, index) => {
                    return (
                        <div className={`flex flex-column align-items-center justify-content-center hover:text-primary ${index === selectedIndex && "text-primary"}`} onClick={() => {setSelectedIndex(index)}}>
                            <i className={`pi pi-${item.icon} text-5xl`}></i>
                            <p className="mb-3 mt-1 text-xs">{item.name}</p>
                        </div>
                    )
                })}
            </div>

            <div className="w-full overflow-y-auto">
                {elements[selectedIndex].element}
            </div>
        </div>
    )
}