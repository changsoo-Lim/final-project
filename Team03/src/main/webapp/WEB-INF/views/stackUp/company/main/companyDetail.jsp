<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
  </head>
  <body>
    <style>
      #container {
        width: 1000px;
        margin: 20px auto;
      }
      .ck-editor__editable[role="textbox"] {
        /* Editing area */
        min-height: 200px;
      }
      .ck-content .image {
        /* Block images */
        max-width: 80%;
        margin: 20px auto;
      }
      .jw {
        display: inline-block;
      }
    </style>
    <button style="font-size: 5em; cursor: pointer" onclick="fJiWon(1)">
      지원 멋졍?
    </button>
    <button style="font-size: 5em; cursor: pointer" onclick="fJiWon(0)">
      지원 바봉?
    </button>
    <div id="container">
      <div id="editor"></div>
    </div>
    <script>
      let jwEditor;

      function ranColor() {
        return ` rgb(
                  ${Math.round(Math.random() * 255)},
                  ${Math.round(Math.random() * 255)},
                  ${Math.round(Math.random() * 255)}
                 )`;
      }

      function fJiWon(isJW) {
        let jwPreData = jwEditor.getData();
        let jwRanNum = Math.ceil(Math.random() * 3) + 2;
        let jwNewData = `<div class="jw" style="color:${ranColor()};font-size:${jwRanNum}em">지원 바봉</div>`;
        if (isJW)
          jwNewData = `<div class="jw" style="color:${ranColor()};font-size:${jwRanNum}em">지원 멋졍</div>`;

        jwEditor.setData(`${jwPreData}${jwNewData}`);
      }
    </script>

    <!--
            The "superbuild" of CKEditor&nbsp;5 served via CDN contains a large set of plugins and multiple editor types.
            See https://ckeditor.com/docs/ckeditor5/latest/installation/getting-started/quick-start.html#running-a-full-featured-editor-from-cdn
        -->
    <script src="https://cdn.ckeditor.com/ckeditor5/41.4.2/super-build/ckeditor.js"></script>
    <script>
      // This sample still does not showcase all CKEditor&nbsp;5 features (!)
      // Visit https://ckeditor.com/docs/ckeditor5/latest/features/index.html to browse all the features.
      CKEDITOR.ClassicEditor.create(document.getElementById("editor"), {
        // https://ckeditor.com/docs/ckeditor5/latest/features/toolbar/toolbar.html#extended-toolbar-configuration-format
        toolbar: {
          items: [
            "exportPDF",
            "|",
            "findAndReplace",
            "selectAll",
            "|",
            "heading",
            "|",
            "bold",
            "italic",
            "strikethrough",
            "underline",
            "code",
            "subscript",
            "superscript",
            "removeFormat",
            "|",
            "bulletedList",
            "numberedList",
            "todoList",
            "|",
            "outdent",
            "indent",
            "|",
            "undo",
            "redo",
            "-",
            "fontSize",
            "fontFamily",
            "fontColor",
            "fontBackgroundColor",
            "highlight",
            "|",
            "alignment",
            "|",
            "link",
            "uploadImage",
            "blockQuote",
            "insertTable",
            "mediaEmbed",
            "codeBlock",
            "|",
            "specialCharacters",
            "horizontalLine",
            "|",
            "sourceEditing",
          ],
          shouldNotGroupWhenFull: true,
        },
        // https://ckeditor.com/docs/ckeditor5/latest/features/headings.html#configuration
        heading: {
          options: [
            {
              model: "paragraph",
              title: "Paragraph",
              class: "ck-heading_paragraph",
            },
            {
              model: "heading1",
              view: "h1",
              title: "Heading 1",
              class: "ck-heading_heading1",
            },
            {
              model: "heading2",
              view: "h2",
              title: "Heading 2",
              class: "ck-heading_heading2",
            },
          ],
        },
        // https://ckeditor.com/docs/ckeditor5/latest/features/editor-placeholder.html#using-the-editor-configuration
        placeholder: "지원 환영 CKEditor 5!",
        // https://ckeditor.com/docs/ckeditor5/latest/features/font.html#configuring-the-font-family-feature
        fontFamily: {
          options: ["default", "Arial, Helvetica, sans-serif"],
          supportAllValues: true,
        },
        // https://ckeditor.com/docs/ckeditor5/latest/features/font.html#configuring-the-font-size-feature
        fontSize: {
          options: [10, 12, 14, "default", 18, 20, 22],
          supportAllValues: true,
        },
        // Be careful with the setting below. It instructs CKEditor to accept ALL HTML markup.
        // https://ckeditor.com/docs/ckeditor5/latest/features/general-html-support.html#enabling-all-html-features
        htmlSupport: {
          allow: [
            {
              name: /.*/,
              attributes: true,
              classes: true,
              styles: true,
            },
          ],
        },
        // https://ckeditor.com/docs/ckeditor5/latest/features/link.html#custom-link-attributes-decorators
        link: {
          decorators: {
            addTargetToExternalLinks: true,
            //   defaultProtocol: "https://",
            toggleDownloadable: {
              mode: "manual",
              label: "Downloadable",
              attributes: {
                download: "file",
              },
            },
          },
        },
        // The "superbuild" contains more premium features that require additional configuration, disable them below.
        // Do not turn them on unless you read the documentation and know how to configure them and setup the editor.
        removePlugins: [
          // These two are commercial, but you can try them out without registering to a trial.
          // 'ExportPdf',
          // 'ExportWord',
          "AIAssistant",
          "CKBox",
          "CKFinder",
          "EasyImage",
          // This sample uses the Base64UploadAdapter to handle image uploads as it requires no configuration.
          // https://ckeditor.com/docs/ckeditor5/latest/features/images/image-upload/base64-upload-adapter.html
          // Storing images as Base64 is usually a very bad idea.
          // Replace it on production website with other solutions:
          // https://ckeditor.com/docs/ckeditor5/latest/features/images/image-upload/image-upload.html
          // 'Base64UploadAdapter',
          "MultiLevelList",
          "RealTimeCollaborativeComments",
          "RealTimeCollaborativeTrackChanges",
          "RealTimeCollaborativeRevisionHistory",
          "PresenceList",
          "Comments",
          "TrackChanges",
          "TrackChangesData",
          "RevisionHistory",
          "Pagination",
          "WProofreader",
          // Careful, with the Mathtype plugin CKEditor will not load when loading this sample
          // from a local file system (file://) - load this site via HTTP server if you enable MathType.
          "MathType",
          // The following features are part of the Productivity Pack and require additional license.
          "SlashCommand",
          "Template",
          "DocumentOutline",
          "FormatPainter",
          "TableOfContents",
          "PasteFromOfficeEnhanced",
          "CaseChange",
        ],
      })
        .then((editor) => {
          jwEditor = editor; // Save for later use.
        })
        .catch((error) => {
          console.error(error);
        });
    </script>
  </body>
</html>