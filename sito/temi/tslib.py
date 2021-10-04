import re
from pathlib import Path
from textwrap import dedent

from pygments import highlight as pygments_highlight
from pygments.lexers import get_lexer_for_filename
from pygments.formatters import HtmlFormatter

from IPython.display import HTML

REMOVE_COMMENT_RE = re.compile(r'(^[ \t]*\/\*(.*?)\*\/(\n|\r)?)|([ \t]*\/\/(?! +(S|E)OF:).*?(\n|\r))', re.DOTALL | re.MULTILINE)

DIRT_DIR = Path('../../java/temi')
CLEAN_DIR = Path('../../../temi-svolti/temi')
CLEAN_URL = 'https://github.com/prog2-unimi/temi-svolti/blob/master/java/temi'

class Solution:

  @staticmethod
  def extract_markers(source):
    lines = []
    label2se = {}
    for line in source.splitlines():
      m = re.match(r'[ \t]*\/\/ +(S|E)OF:(.*)', line)
      if m:
        what = m.group(1).strip()
        label = m.group(2).strip()
        if what == 'S':
          label2se[label] = (len(lines), None)
        else:
          label2se[label] = (label2se[label][0], len(lines))
      else:
        lines.append(line)
    return label2se, lines

  def __init__(self, name):
    self.name = name
    self._label2se, self._lines = {}, {}
    for path in (DIRT_DIR / name).glob('*.java'):
      source = path.read_text()
      _, clean = Solution.extract_markers(source)
      if CLEAN_DIR.is_dir():
        (CLEAN_DIR / name / path.name).write_text('\n'.join(clean))
      self._label2se[path.stem], self._lines[path.stem] = Solution.extract_markers(REMOVE_COMMENT_RE.sub('', source))

  def show(self, cls, fragment = None, highlight = None, linenos = False):
    label2se, lines = self._label2se[cls], self._lines[cls]
    first, last = label2se[fragment] if fragment else (0, len(lines))
    url = f'{CLEAN_URL}/{self.name}/{cls}.java'
    if first > 0 or last < len(lines):
        url += '#L{}-L{}'.format(first + 1, last)
    code = '\n'.join(lines[first:last])
    if highlight:
      highlight = [l - first + 1 for l in range(*label2se[highlight])]
    lexer = get_lexer_for_filename(cls + '.java', stripall = False)
    formatter = HtmlFormatter(
        linenos = 'inline' if linenos else None,
        linenostart = 1 if first is None else 1 + first,
        cssclass = 'output_html',
        nobackground = False,
        hl_lines = highlight if highlight else []
    )
    return HTML('<style>{}</style>{}<p><a href="{}" target="_blank">[sorgente]</a>'.format(
        formatter.get_style_defs('.output_html'),
        pygments_highlight(dedent(code), lexer, formatter),
        url
    ))