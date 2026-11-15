from pathlib import Path
from xml.etree import ElementTree


def string_names(path: Path) -> set[str]:
    return {
        element.attrib["name"]
        for element in ElementTree.parse(path).getroot().findall("string")
    }


resources = Path("app/src/main/res")
english = string_names(resources / "values/strings.xml")
german = string_names(resources / "values-de/strings.xml")
if english != german:
    raise SystemExit(
        f"String resources differ: missing German={sorted(english - german)}, "
        f"extra German={sorted(german - english)}"
    )
print(f"Verified {len(english)} English and German string resources.")
