import TaskMain from "components/Task/Task";

export default function Task() {
  return (
    <div className="overflow-y-hidden">
      <TaskMain />
    </div>
  );
}

export async function getStaticProps(context) {
  return {
    props: {
      secured: true,
    },
  };
}
