from html_to_markdown import convert_to_markdown


class SummaryService:
    def __init__(self):
        pass

    def generate_summary(self, html_content: str) -> str:
        markdown_content = convert_to_markdown(html_content)
        # Here you would implement your summarization logic
        return markdown_content
