import { Button } from "primereact/button";
import { FileUpload } from "primereact/fileupload";

export default function TaskDetail({ task, setTaskId, taskUser}) {

    const handleGoBackClick = async () => {
      setTaskId(null);
  };


  return (
    <div className="flex flex-column h-full">
      <div className="border-noround border-none card p-2 flex flex-row justify-content-between top-0 sticky">
        <div className="flex align-items-center justify-content-start">
            <Button onClick={handleGoBackClick} className="border-solid border-white">
            <i className="pi pi-angle-left" style={{ fontSize: '1.5rem', marginRight: "12px" }}></i>Go Back</Button>
        </div>
        <div className="flex align-items-center justify-content-end">
            <div className="flex flex-row align-items-center border-round-lg">
                <p className="m-3">{task.updatedAt ? "" : "Zurückgeben"}</p>
                <Button className="border-solid border-white" onClick={() => console.log("TODO")}>{task.updatedAt ? "Abgeben" : "Erneut Abgeben"}</Button>
            </div>
          </div>
      </div>

      <div className="flex flex-row p-6 justify-content-between align-items-start">
        <div className="">
        <h2>{task.title}</h2>
        <p>Date: {task.createdAt}</p>
        <h3>{task.description}</h3>
        <p>Status: {task.status}</p>
        <FileUpload name="demo[]" url={'/api/upload'} multiple accept="pdf/*" maxFileSize={1000000} emptyTemplate={<p className="m-0">Drag and drop files to here to upload.</p>} />
        </div>
        <div className="">
            <p>MaxPoints: </p>
            <p>Points: </p>
        </div>
        <div>

        </div>
      </div>
    </div>
  );
}
