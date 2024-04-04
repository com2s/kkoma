interface titleProp {
  title: string;
  subtitle: string;
}

export default function Title({ title, subtitle }: titleProp) {
  return (
    <div className="flex flex-col gap-2 w-full break-keep	">
      <h2 className="whitespace-pre-wrap">{title}</h2>
      <span className="text-caption c-text3 whitespace-pre-wrap">{subtitle}</span>
    </div>
  );
}
