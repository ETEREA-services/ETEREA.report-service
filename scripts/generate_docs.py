import json
from datetime import datetime
from jinja2 import Template
import os

def load_data():
    """Load issues and milestones data from JSON files"""
    with open('data/issues.json', 'r') as f:
        issues = json.load(f)
    with open('data/milestones.json', 'r') as f:
        milestones = json.load(f)
    return issues, milestones

def generate_docs():
    """Generate project documentation from issues and milestones data"""
    issues, milestones = load_data()
    
    # Documentation template
    template_str = """---
layout: default
title: Project Documentation
---

# Project Documentation

_Last updated: {{ current_date }}_

## Milestones Summary

| Milestone | Status | Due Date |
|-----------|--------|----------|
{% for milestone in milestones %}
| {{ milestone.title }} | {{ milestone.state }} | {{ milestone.due_on if milestone.due_on else 'Not set' }} |
{% endfor %}

## Milestone Details

{% for milestone in milestones %}
### {{ milestone.title }}
**Status:** {{ milestone.state }}
**Description:** {{ milestone.description }}
{% if milestone.due_on %}**Due Date:** {{ milestone.due_on }}{% endif %}

---
{% endfor %}

## Active Issues

{% for issue in issues if issue.state == 'open' %}
### #{{ issue.number }}: {{ issue.title }}
**Status:** {{ issue.state }}
**Created:** {{ issue.created_at }}
{% if issue.milestone %}**Milestone:** {{ issue.milestone }}{% endif %}
{% if issue.labels %}**Labels:** {{ issue.labels|join(', ') }}{% endif %}

{{ issue.body }}

---
{% endfor %}

## Closed Issues

{% for issue in issues if issue.state == 'closed' %}
### #{{ issue.number }}: {{ issue.title }}
**Status:** {{ issue.state }}
**Created:** {{ issue.created_at }}
**Closed:** {{ issue.closed_at }}
{% if issue.milestone %}**Milestone:** {{ issue.milestone }}{% endif %}
{% if issue.labels %}**Labels:** {{ issue.labels|join(', ') }}{% endif %}

{{ issue.body }}

---
{% endfor %}
"""
    
    template = Template(template_str)
    doc_content = template.render(
        current_date=datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
        issues=issues,
        milestones=milestones
    )
    
    # Save documentation
    with open('docs/project-documentation.md', 'w') as f:
        f.write(doc_content)

if __name__ == '__main__':
    generate_docs() 