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
CLEAN_URL = 'https://github.com/prog2-unimi/temi-svolti/blob/master/temi'
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
    self._ghse, self._lse, self._lines = {}, {}, {}
    for path in (DIRT_DIR / name).glob('*.java'):
      source = path.read_text()
      self._ghse[path.stem], clean = Solution.extract_markers(source)
      if CLEAN_DIR.is_dir():
        (CLEAN_DIR / name / path.name).write_text('\n'.join(clean))
      self._lse[path.stem], self._lines[path.stem] = Solution.extract_markers(REMOVE_COMMENT_RE.sub('', source))

  def show(self, cls, fragment = None, highlight = None, linenos = False):
    ghse, lse, lines = self._ghse[cls], self._lse[cls], self._lines[cls]
    first, last = lse[fragment] if fragment else (0, len(lines))
    url = f'{CLEAN_URL}/{self.name}/{cls}.java'
    if fragment: url += f'#L{ghse[fragment][0]+1}-L{ghse[fragment][1]}'
    code = '\n'.join(lines[first:last])
    if highlight:
      highlight = [l - first + 1 for l in range(*lse[highlight])]
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