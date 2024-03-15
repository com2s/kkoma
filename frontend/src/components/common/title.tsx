interface titleProp {
  title: string;
  subtitle: string;
}

export default function Title({ title, subtitle }: titleProp) {
  return (
    <div className="flex flex-col gap-2 w-40 break-keep	">
      <h2>{title}</h2>
      <span className="text-caption c-text3">{subtitle}</span>
    </div>
  );
}
